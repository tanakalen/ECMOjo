#!/usr/bin/env python3
import sys, re, csv
import argparse

def main(argv=None):
    #Command line setup
    parser = argparse.ArgumentParser(prog='create-i18n-scn',
        description='Create scn file, display stdout.',
        epilog="Ex:\n\tcreate-i18n-scn i18nstrings.csv")
    parser.add_argument('infile',
        nargs='?', type=argparse.FileType('r'),
        default=sys.stdin)
    args = parser.parse_args()
    #Open file
    with open(args.infile.name, 'r') as f:
        document = f.read()
        ldoc = re.split('# Baseline Values', document) #FRAGILE
        lines = re.split('\n', ldoc[0])
        plines = [x for x in lines 
            if not re.search("trigger = |^#|goal =| = \*", x) ]
        output = [ line.split(' = ') for line in plines if ' = ' in line ]
        [print(item[0]) for item in output]
        print("------")
        [print(item[1]) for item in output]

if __name__ == "__main__":
    try:
        exitcode = main()
    except Exception as err:
        print('ERROR: {}'.format(err), file=sys.stderr)
        exitcode = 1
    sys.exit(exitcode)
    sys.exit(main())