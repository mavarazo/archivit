import logging
import os
import yaml

class Config():

    def __init__(self):
        self.logger = logging.getLogger(__name__)

        with open('/app/config/config.yml', 'r') as stream:
            try:
                self.config = yaml.safe_load(stream)
                self.logger.info(f"Loaded {len(self.get_rules())} rules")
            except yaml.YAMLError as ex:
                self.logger.error(ex)

    
    def get_rules(self):
        return self.config['rules']