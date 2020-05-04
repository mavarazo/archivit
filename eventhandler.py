import logging
import os

from client import NextcloudClient
from processor import ArchivitProcessor


class ArchivitEventHandler:

    def __init__(self):
        self.logger = logging.getLogger(__name__)
        self.client = NextcloudClient()
        self.processor = ArchivitProcessor()

    def check(self):
        file_names = self.client.check_input_path()
        for file_name in file_names or []:
            self.logger.info(f"processing {file_name}")
            temp_file = self.client.download_from_input_path(file_name)
            rule = self.processor.process(temp_file)
            os.remove(temp_file)
            if rule:
                self.logger.info(f"{file_name} matches with {rule['name']}")
                self.client.move_from_input_path(file_name, rule['target'])
            else:
                self.logger.warning(f"{file_name} matches with no rule")
