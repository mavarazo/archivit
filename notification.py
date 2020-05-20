import logging
import os
import smtplib
import ssl


class Email:

    def __init__(self):
        self.logger = logging.getLogger(__name__)

        self.email_from = os.getenv('NOTIFICATION_EMAIL_FROM')
        self.email_to = os.getenv('NOTIFICATION_EMAIL_TO')
        self.email_server = os.getenv('NOTIFICATION_EMAIL_SERVER')
        self.email_username = os.getenv('NOTIFICATION_EMAIL_SERVER_USER')
        self.email_password = os.getenv('NOTIFICATION_EMAIL_SERVER_PASSWORD')


    def send(self, subject, message):
        body = (
            f"Subject: {subject}\n"
            f"\n"
            f"{message}"
        )

        context = ssl.create_default_context()
        with smtplib.SMTP(self.email_server, 587) as server:
            server.ehlo() 
            server.starttls(context=context)
            server.ehlo()
            server.login(self.email_username, self.email_password)
            server.sendmail(self.email_from, self.email_to, body)
            self.logger.info(f"Send email notification to {self.email_to}")
