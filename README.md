# Archivit

Archivit is a Python app for organizing PDF (with OCR).

## Installation

Copy `.env.example` to `.env? for your personal configuration.

```bash
$ cat .env 
PYTHONDONTWRITEBYTECODE=true

# Container Configuration
DOCKER_RESTART_POLICY=no
DOCKER_STOP_GRACE_PERIOD=3s

# Volumes
APP_CONFIG_VOLUME=.:/app/config

# Nextcloud Connection
NEXTCLOUD_HOST=https://localhost
NEXTCLOUD_WEBDAV_PATH=/remote.php/dav/files/<Username>
NEXTCLOUD_USERNAME=Username
NEXTCLOUD_PASSWORD=YouWouldNeverGuess
NEXTCLOUD_INPUT_PATH=/Archive/Temp
NEXTCLOUD_OUTPUT_PATH=/Archive
```

Use `docker-compose` to run Archivit.

```bash
docker-compose up
```

## Usage

Edit `config.yml` so Archivit knows what to do with incoming PDF.

```bash
$ cat config.yml
---
rules:
- name: bingo
  target: Bingo
  keywords:
  - bingo
  - foo
- name: bongo
  target: Bongo
  keywords:
  - bongo
  - bar
```

In the example above, incoming PDF would be checked with those rules. Archivit searches for at least 50 % machtes of the `keywords` in the PDF content. If a rule machtes it will move the PDF into `target`-folder.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
