#####################################################
"""
Programmer: Austin Ward
Lab: Logsdon Lab
Date Updated: 8/25/16
Description: This program uses the tblastn algorithm with a list of genes(protein), in fasta format, against a list of genome assemblies.
             With an e-value threshold given by the user, the program will return the scaffold sequences that are below the user given threshold.
             The blastx algorithm is than used to rerun the list of genes(protein) against the scaffolds returned from the first part (xml file)
             The xml file is than parsed to recover a more narrowed down sequence that the gene could possible be in the scaffold. An algorithm is
             is used to find the min and max locations and highlight areas that are overlapped between the min and max found.



"""
#####################################################

from __future__ import with_statement
from Bio.Blast import NCBIWWW
from Bio.Blast import NCBIXML
from contextlib import contextmanager
from Bio.Blast.Applications import NcbiblastxCommandline

import signal
import sys
import os
import time
import fileinput

#information[0] = organism name
#information[1] = gca
#information[2] = id
#information[3] = type
#information[4] = subtype
names = []
GCAs =[]
IDs = []
types = []
subtypes = []

#Timer function made by Josh Lee - stackoverflow.com
class TimeoutException(Exception): pass

@contextmanager
def time_limit(seconds):
    def signal_handler(signum, frame):
        raise TimeoutException, "Timed out!"
    signal.signal(signal.SIGALRM, signal_handler)
    signal.alarm(seconds)
    try:
        yield
    finally:
        signal.alarm(0)

def blast(idnum,gcanum,searchquery):

    try:
        with time_limit(480):
            results = NCBIWWW.qblast("tblastn","genomic/"+idnum+"/"+gcanum, sequence=searchquery, format_type="XML")
    except TimeoutException, msg:
        print "Timed out!"
        return -1

    try:
        blast_record = NCBIXML.read(results)
    except KeyboardInterrupt and ValueError and StandardError:
        print("XML ERROR")
        return -2
    else:
        return blast_record

#information[0] = organism name
#information[1] = organism gca #
#information[2] = organism id #
#information[3] = organism type
#information[4] = organism subtype

#ask user for input file
for lines in fileinput.input(sys.argv[1]):
    information =lines.split()

    names.append(information[0])
    GCAs.append(information[1])
    IDs.append(information[2])
    types.append(information[3])
    subtypes.append(information[4])


geneIDs = []

with open("RAD51.fas") as lines2:
    geneIDs = lines2.read().splitlines()

with open("RAD51seqs.fasta") as lines3:
    geneSeq = lines3.read()

e_value_thresh =  0.0000000010 #get this number from the user may change depending on their specs

print geneIDs

count = 0
print os.getcwd()
while count < len(names):

    descriptions = []
    tblastnReturn = []
    gene = 0
    while gene < 1:

        hold = []
        xx = 0
        print "Gene: %d", gene
        print "Genome: %d", GCAs[count]

        blast_record = blast(IDs[count], GCAs[count], geneIDs[gene])

        if blast_record != -1 and blast_record != -2:
                for alignment in blast_record.alignments:
                    for hsp in alignment.hsps:
                        if hsp.expect < e_value_thresh:
                            if alignment.title not in hold:
                                hold.append(alignment.title)  # holds current returned(reset each time)
                                if (hold[xx] not in descriptions):
                                    descriptions.append(hold[xx])
                                    tblastnReturn.append(hsp.sbjct[0:])
                                    print(hold[xx])


                                xx += 1

        gene += 1


    x = 0

    fasta = []
    while x < len(descriptions):

        #fasta format tblastn returns
        fasta.append(">"+descriptions[x]+'\n'+tblastnReturn[x]+'\n')
        x += 1

    print fasta


    NcbiblastxCommandline(cmd="blastx", query=fasta, subject=lines3, out="blastxTest.xml", outfmt=5)
    result_handle = open("blastxTest.xml")

    blast_record = NCBIXML.read(result_handle)
    print blast_record.alignments



    print "here"


    count += 1


















