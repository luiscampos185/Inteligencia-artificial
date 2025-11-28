from transformers import pipeline
import scipy.io.wavfile
import numpy as np

print(" [VOZ] Cargando cargando voz... ---")

tts_pipe = pipeline("text-to-speech", model="facebook/mms-tts-spa", framework="pt")

def generar_audio(texto, nombre_archivo="salida.wav"):
    print(f"--- [VOZ] Generando voz para: '{texto}' ---")
    
    if not texto or len(texto) < 2:
        print("--- [VOZ] Texto insuficiente. No se generará audio.")
        return

    audio_data = tts_pipe(texto)
    audio_array = audio_data["audio"]
    rate = audio_data["sampling_rate"]

    audio_array = np.array(audio_array)
    
    audio_array = audio_array.squeeze()
    
    audio_array = np.clip(audio_array, -1.0, 1.0)
    audio_int16 = (audio_array * 32767).astype(np.int16)

    try:
        scipy.io.wavfile.write(nombre_archivo, rate, audio_int16)
        print(f"--- [VOZ] EXITO: Archivo guardado como '{nombre_archivo}' ---")
    except Exception as e:
        print(f"--- [VOZ] ERROR al guardar audio: {e}")