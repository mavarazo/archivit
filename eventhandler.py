import logging
import os

from client import NextcloudClient
from notification import Email
from processor import ArchivitProcessor


class ArchivitEventHandler:

    def __init__(self):
        self.logger = logging.getLogger(__name__)
        self.client = NextcloudClient()
        self.processor = ArchivitProcessor()
        self.email = Email()
            

    def check(self):
        file_names = self.client.check_input_path()
        for file_name in file_names or []:
            self.logger.info(f"processing {file_name}")
            temp_file = self.client.download_from_input_path(file_name)
            rule, matches, score = self.processor.process(temp_file)
            os.remove(temp_file)
            if rule:
                self.logger.info(f"{file_name} matches with {rule['name']}")
                self.client.move_from_input_path(file_name, rule['target'])
                self.notify_success(file_name, rule, matches, score)
            else:
                self.logger.warning(f"{file_name} matches with no rule")
                self.notify_warning(file_name)


    def notify_success(self, file_name, rule, matches, score):
        subject = f"Archivit processed {file_name}"
        message = (
            f"File:\t\t\t{file_name}\n"
            f"Rule:\t\t{rule['name']}\n"
            f"Target:\t\t{rule['target']}\n"
            f"Keywords:\t{','.join([str(v) for v in rule['keywords']])}\n"
            f"Matches:\t\t{','.join([str(v) for v in matches])}\n"
            f"Score:\t\t{score}\n"
        )
        self.email.send(subject, message)

    
    def notify_warning(self, file_name):
        subject = f"Archivit not processed {file_name}"
        message = (
            f"File \"{file_name}\" didn't match with any rules.\n"
            f"Please check your config."
        )                
        self.email.send(subject, message)
