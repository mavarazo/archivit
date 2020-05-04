import logging
import os
import sys
import time

import schedule

from eventhandler import ArchivitEventHandler


logging.basicConfig(level=logging.INFO,
                        format='%(asctime)s - %(message)s',
                        datefmt='%Y-%m-%d %H:%M:%S')
    
logger = logging.getLogger(__name__)


def main():
    try:
        eventhandler = ArchivitEventHandler()
        eventhandler.check()
    except Exception as ex:
        logger.error(ex)


if __name__ == "__main__":
    logger.info(f"Starting")
    main()
    
    schedule.every(15).minutes.do(main)
    while True:
        schedule.run_pending()
        time.sleep(1)
