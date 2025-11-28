import gradio as gr
import cv2
import os
import vision
import generadorVoz

def pipeline_gradio(imagen_entrada):
    if imagen_entrada is None:
        return "No subiste ninguna imagen.", None

    temp_path = "temp_gradio_input.jpg"
    
    imagen_bgr = cv2.cvtColor(imagen_entrada, cv2.COLOR_RGB2BGR)
    cv2.imwrite(temp_path, imagen_bgr)
    
    print(f"Enviando {temp_path} a vision.py...")
    try:
        texto_detectado = vision.leer_texto(temp_path)
    except Exception as e:
        return f"Error en Visión: {e}", None
    
    if not texto_detectado:
        texto_detectado = "Error: No se pudo leer texto."

    print(f"Enviando texto a generadorVoz.py...")
    nombre_audio_salida = "audio_gradio_final.wav"
    
    try:
        generadorVoz.generar_audio(texto_detectado, nombre_audio_salida)
    except Exception as e:
         return texto_detectado, None

    return texto_detectado, nombre_audio_salida

iface = gr.Interface(
    fn=pipeline_gradio,
    inputs=gr.Image(type="numpy", label="Sube tu imagen manuscrita"),
    outputs=[
        gr.Textbox(label="Texto Reconocido"),
        gr.Audio(label="Audio Generado")
    ],
    title="Sistema de texto a Voz",
    description="Proyecto de Reconocimiento de texto y generacion de voz"
)

if __name__ == "__main__":
    iface.launch()