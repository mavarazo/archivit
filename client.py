import logging
import os
from io import BytesIO

from webdav3.client import Client


class NextcloudClient(Client):

    def __init__(self):
        options = {
            'webdav_hostname': os.getenv('NEXTCLOUD_HOST'),
            'webdav_login': os.getenv('NEXTCLOUD_USERNAME'),
            'webdav_password': os.getenv('NEXTCLOUD_PASSWORD'),
            'webdav_root': os.getenv('NEXTCLOUD_WEBDAV_PATH')
        }

        super().__init__(options)

        self.logger = logging.getLogger(__name__)
        self.temp_path = os.getenv('TEMP_PATH', '/app/temp')
        self.input_path = os.getenv('NEXTCLOUD_INPUT_PATH')
        self.output_path = os.getenv('NEXTCLOUD_OUTPUT_PATH')


    def check_input_path(self):
        result = self.list(self.input_path)
        if result:
            return filter(lambda x: x.endswith('.pdf'), result)
        return None


    def download_from_input_path(self, file_name):
        self.download_sync(f"{self.input_path}/{file_name}", f"{self.temp_path}/{file_name}")
        return f"{self.temp_path}/{file_name}"

    def move_from_input_path(self, file_name, target):
        if not self.check(f"{self.output_path}/{target}"):
            self.mkdir(f"{self.output_path}/{target}")

        self.move(f"{self.input_path}/{file_name}", f"{self.output_path}/{target}/{file_name}")
