#!/usr/bin/python

from __future__ import with_statement
from Bio.Blast import NCBIWWW
from Bio.Blast import NCBIXML
from contextlib import contextmanager

import cgi, cgitb
from StringIO import StringIO
import json
from io import BytesIO
##import pycurl


import signal
import sys
import os
import time
import fileinput


cgitb.enable()

form = cgi.FieldStorage()


e_value_thresh = form.getvalue('e_value_thresh')
fasta = form.getvalue('fasta')
kingdoms = form.getvalue('kingdoms')


print e_value_thresh
print fasta
print kingdoms

######################################################################################################
#                                                                                                    #
#                                              Blast                                                 #
#                                                                                                    #
######################################################################################################

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

def blast(id, assembly ,query):

    try:
        with time_limit(480):
            results = NCBIWWW.qblast("tblastn", "genomic/"+id+"/"+assembly, sequence=query, format_type="XML")
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


######################################################################################################
#                                                                                                    #
#                                        Genome/Gene Loop                                            #
#                                                                                                    #
######################################################################################################

names = []
GCAs = []
types = []
subtypes = []

GenBank_FTP = []
RefSeq_FTP = []

information = []

e_value_thresh = 0.0000000010

for lines in open('Animal_scaffold_genomes_info.txt'):

    if lines[0][0] != "#":

        information =lines.split('\t')

        names.append(information[0])
        types.append(information[4])
        subtypes.append(information[5])
        GCAs.append(information[8])
        RefSeq_FTP.append(information[17])
        GenBank_FTP.append(information[18])
lines = 0

genes = []

for lines in open('RAD51.fas'):
    genes.append(lines.splitlines())


genome_counter = 0

while genome_counter < len(names):

    gene_counter = 0


    descriptions = []
    tblastnReturn =[]

    while gene_counter < 1:

        hold = []
        xx = 0
        blast_record = blast(names[genome_counter], query=genes[gene_counter][0])

        #get contig sequence
        #store in file
            #possibly multi-thread so that while the blastx function is doing work, tblastn can begin
            # on the next genome and/or gene
        #send file to blastx function (done locally)

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

        gene_counter+=1


    genome_counter+=1
