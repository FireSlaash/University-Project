from fastapi import FastAPI, UploadFile, HTTPException
import shutil
import os
import subprocess

app = FastAPI()

# Configuration des répertoires
BASE_DIR = os.getcwd()

@app.post("/upload/")
async def sync_pylock(file: UploadFile):
    # sauvegarder le fichier reçu
    local_file_path = os.path.join(BASE_DIR, file.filename)

    try:
        #Enregistrement physique du fichier sur le serveur
        with open(local_file_path, "wb") as buffer:
            shutil.copyfileobj(file.file, buffer)
        
        os.system('uv pip sync pylock.toml') # Synchroniser les dépendances avec pylock.toml
        return "good"
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))