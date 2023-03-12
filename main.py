import sys

sys.path.append(r"c:\users\nijash sooriya\appdata\local\programs\python\python39\lib\site-packages")
import io
import tensorflow as tf
import tflite_model_maker as mm
from tflite_model_maker import audio_classifier
import tensorflow_hub as hub
import librosa
import os
import numpy as np
import matplotlib.pyplot as plt
import random
import pandas as pd


class AudioClassifier:
    yamnet_model = hub.load('https://tfhub.dev/google/yamnet/1')

    def get_rand_audio(self, dir):
        # Choose random sound file in ambulance directory
        ambulance_instance = random.choice(os.listdir(dir))
        file_path = os.path.join(dir, ambulance_instance)
        return file_path

    # Resamples a random waveform chosen from the directory to single channel, 16 KHz
    def resample_waveform(self, dir):
        y, sr = librosa.load(dir, sr=None, mono=False)

        # Convert stereo to mono
        if y.ndim > 1:
            y = librosa.to_mono(y)

        # Resample to 16kHz
        y_resampled = librosa.resample(y, orig_sr=sr, target_sr=16000)

        return y_resampled

    def plot_waveform(self, waveform):
        sample_wav = self.resample_waveform(waveform)
        plt.plot(sample_wav)
        plt.show()

    def YAMNet_instantiation(self, wav_data):

        class_map_path = self.yamnet_model.class_map_path().numpy().decode('utf-8')
        class_names = list(pd.read_csv(class_map_path)['display_name'])

        scores, embeddings, spectrogram = self.yamnet_model(wav_data)
        class_scores = tf.reduce_mean(scores, axis=0)
        top_class = tf.math.argmax(class_scores)
        inferred_class = class_names[top_class]
        return [inferred_class, embeddings]

    def mymodel_instantiation(self, wav_data):
        #Base Model
        spec = audio_classifier.YamNetSpec(
            keep_yamnet_and_custom_heads=True,
            frame_step=3 * audio_classifier.YamNetSpec.EXPECTED_WAVEFORM_LENGTH,
            frame_length=6 * audio_classifier.YamNetSpec.EXPECTED_WAVEFORM_LENGTH)


if __name__ == '__main__':
    ### Start of waveform loading process

    ambulance_dir = r"C:\Users\Nijash Sooriya\Desktop\ambulance"
    classifier = AudioClassifier()

    # Pull a random file from ambulance directory
    random_sample = classifier.get_rand_audio(ambulance_dir)

    # Resample the random waveform to specs
    resampled_wav = classifier.resample_waveform(random_sample)

    # Plot the waveform
    classifier.plot_waveform(random_sample)

    ### End of waveform loading process

    ### YAMNet Model Deployment Start
    classification = classifier.YAMNet_instantiation(resampled_wav)
    print(f'The classified sound is {classification[0]}')
    print(f'The embedding shape is {classification[1].shape}')


    ### YAMNet Model Deployment End
