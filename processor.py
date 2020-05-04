import logging
import os
from pathlib import Path

from config import Config


class ArchivitProcessor():

    def __init__(self):
        self.logger = logging.getLogger(__name__)
        self.rules = Config().get_rules()


    def process(self, file_path):
        result = list()
        content = self._get_pdf_content(file_path)
        if content:
            for rule in self.rules:
                if rule['keywords']:
                    maximum = len(rule['keywords'])
                    matches = len(self._words_in_string(rule['keywords'], content))
                    score = 100 / maximum * matches
                    if score > 49:
                        result.append((rule, maximum, matches, score))

        return max(result, key = lambda i : i[3])[0] if result else None


    def _get_pdf_content(self, file_path):
        with os.popen('/usr/bin/pdftotext %s -' % file_path) as p:
            output = p.read()
        return output
        

    def _words_in_string(self, word_list, a_string):
        return set(word_list).intersection(a_string.split())
