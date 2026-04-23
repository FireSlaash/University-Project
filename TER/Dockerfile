FROM python:3.12-slim

COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/

RUN uv pip install --system requests

RUN mkdir /tmp/uv_cache
ENV UV_CACHE_DIR=/tmp/uv_cache
#RUN mkdir -p /app && chown 1000:1000 /app 
# DÉFINIT LE DOSSIER D'ACCUEIL
WORKDIR /app
#USER 1000

# COPIE LE FICHIER 
COPY requestPackage.py .


# Commande pour tester si le fichier est là au démarrage
CMD ["bash"]