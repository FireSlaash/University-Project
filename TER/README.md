Travaux encadrés de recherche sous la supervision de M. Nicolas Thiéry
Sujet : Partage du cache uv entre plusieurs environnements Docker et centralisé sur un serveur. (Protoype)

Comment ça marche : 

Sur un autre terminal pour lancer le serveur (nécessite uvicorn et FASTApi) : 
 ```uvicorn server:app --host 0.0.0.0 --port 8000 --reload```

Se placer dans le répertoire des fichiers: 
Créer une image docker avec ```docker build -t image1 .```

Lancer cet environnement avec ```docker run -it --rm -v ~/.cache/uv:/root/.cache/uv:ro -e UV_NO_SYNC=1 -e UV_LINK_MODE=symlink --add-host=host.docker.internal:host-gateway image1 ```

Dans le conteneur : 

Resolution : uv add pandas

Appelle le serveur pour l'installation : python requestPackages

Synchronisation : uv sync --offline 

Test : uv run python | import pandas


Si on lance un autre environnement, l'installation sera instantanée car pandas sera déjà dans le cache du serveur.
