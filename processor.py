import logging
import os
from pathlib import Path

from config import Config


class ArchivitProcessor():

    def __init__(self):
        self.logger = logging.getLogger(__name__)
        self.rules = Config().get_rules()


    def process(self, file_path):
        self.logger.info(f"{file_path} processing")
        results = list()
        content = self._get_pdf_content(file_path)
        if content:
            for rule in self.rules:
                if rule['keywords']:
                    matches = self._words_in_string(rule['keywords'], content)
                    score = 100 / len(rule['keywords']) * len(matches)
                    self.logger.info(f"{file_path} {rule['name']} {score}")
                    if score == 100:
                        return list((rule, matches, score))
                    elif score > 64:
                        results.append((rule, matches, score))
        
        return results

    def _get_pdf_content(self, file_path):
        with os.popen('/usr/bin/pdftotext "%s" -' % file_path) as p:
            output = p.read()
        return output
        

    def _words_in_string(self, word_list, a_string):
        return set(word_list).intersection(a_string.split())
