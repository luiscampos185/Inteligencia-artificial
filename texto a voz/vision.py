import cv2
import numpy as np
from transformers import pipeline
from PIL import Image

print("--- [VISIÓN] Cargando modelo TrOCR (PyTorch)... ---")

#ocr_pipe = pipeline("image-to-text", model="microsoft/trocr-base-handwritten", framework="pt")
ocr_pipe = pipeline("image-to-text", model="microsoft/trocr-large-handwritten", framework="pt") #segun este funciona mejor

def preprocesar_imagen(ruta_imagen):
    img = cv2.imread(ruta_imagen)
    if img is None: return None

    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    target_height = 384
    aspect_ratio = gray.shape[1] / gray.shape[0]
    target_width = int(target_height * aspect_ratio)
    
    resized = cv2.resize(gray, (target_width, target_height))

    clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8,8))
    enhanced = clahe.apply(resized)

    img_rgb = cv2.cvtColor(enhanced, cv2.COLOR_GRAY2RGB)
    
    return Image.fromarray(img_rgb)

def leer_texto(ruta_imagen):
    print(f"--- [VISIÓN] Procesando imagen: {ruta_imagen} ---")
    
    pil_image = preprocesar_imagen(ruta_imagen)
    if pil_image is None:
        return "ERROR: No se pudo cargar la imagen."

    resultado = ocr_pipe(pil_image, max_new_tokens=20)
    texto = resultado[0]['generated_text']
    
    return texto