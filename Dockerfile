FROM python:3.8.2-slim-buster

WORKDIR /app/src

RUN pip install --upgrade pip

ENV BUILD_DEPS="build-essential" \
    APP_DEPS="poppler-utils"
COPY requirements.txt ./

RUN apt-get update \
  && apt-get install -y ${BUILD_DEPS} ${APP_DEPS} --no-install-recommends \
  && pip install --no-cache-dir -r requirements.txt \
  && rm -rf /var/lib/apt/lists/* \
  && rm -rf /usr/share/doc && rm -rf /usr/share/man \
  && apt-get purge -y --auto-remove ${BUILD_DEPS} \
  && apt-get clean

COPY . .

RUN mkdir -p /app/temp

VOLUME /app/config

CMD [ "python", "./archivit.py" ]

