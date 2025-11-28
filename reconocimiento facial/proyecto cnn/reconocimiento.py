import cv2
import numpy as np
from keras.models import load_model
from keras.preprocessing.image import img_to_array
from keras.applications.mobilenet_v2 import preprocess_input

print("Cargando sistema...")
model = load_model('mi_modelo_facial.h5')

face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

class_names = [
    '---',
    'Angelina Jolie',
    'Brad Pitt',
    'GustavoVega',
    'Hugh Jackman',
    'Jennifer Lawrence',
    'Leonardo DiCaprio',
    'LuisCampos',
    'Megan Fox',
    'Natalie Portman',
    'Nicole Kidman',
    'Scarlett Johansson',
    'Will Smith'
]

cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()
    if not ret: break

    frame = cv2.flip(frame, 1)
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    faces = face_cascade.detectMultiScale(gray, 1.3, 5, minSize=(60, 60))

    for (x, y, w, h) in faces:
        start_x, start_y = max(0, x), max(0, y)
        end_x, end_y = min(frame.shape[1], x + w), min(frame.shape[0], y + h)
        
        if start_x >= end_x or start_y >= end_y: continue

        face_roi = frame[start_y:end_y, start_x:end_x]
        
        try:
            face_rgb = cv2.cvtColor(face_roi, cv2.COLOR_BGR2RGB)
            face_resized = cv2.resize(face_rgb, (224, 224))
            
            img_array = img_to_array(face_resized)
            img_array = np.expand_dims(img_array, axis=0)
            
            img_array = preprocess_input(img_array)

            prediction = model.predict(img_array, verbose=0)
            class_id = np.argmax(prediction[0])
            confidence = np.max(prediction[0]) * 100

            if confidence > 70 and class_id != 0:
                name = class_names[class_id]
                color = (0, 255, 0)
                text = f"{name}: {confidence:.1f}%"
            else:
                name = "Desconocido"
                color = (0, 0, 255)
                text = f"{name} ({confidence:.1f}%)"

            cv2.rectangle(frame, (x, y), (x+w, y+h), color, 2)
            cv2.putText(frame, text, (x, y-10), cv2.FONT_HERSHEY_SIMPLEX, 0.7, color, 2)
            
        except Exception as e:
            pass

    cv2.imshow('Reconocimiento Facial', frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()