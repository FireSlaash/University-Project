import requests
import os
from pathlib import Path

os.system('uv lock') # Mettre uv lock
os.system('uv export -o pylock.toml')
url = "http://host.docker.internal:8000/upload/"

files={'file': open('pylock.toml','r')}


response = requests.post(url, files=files)
print(response.text)


ADMIN_CACHE = Path("/root/.cache/uv")
USER_CACHE = Path("/tmp/uv_cache")

def lier_cache(src_root, dest_root):


    for src_file in src_root.rglob("*"):
        
        relative_path = src_file.relative_to(src_root)

        # On ignore les dossiers qui vont être créés après et les .lock
        if src_file.is_dir() or src_file.name == ".lock":
            continue
        
        # Chemin cible dans USER_CACHE
        dest_file = dest_root / relative_path

        if not dest_file.exists():
            try:
                dest_file.parent.mkdir(parents=True, exist_ok=True)
                dest_file.symlink_to(src_file)
                print(f"Lien créé : {relative_path}")
            except Exception as e:
                continue
    


lier_cache(ADMIN_CACHE, USER_CACHE)
