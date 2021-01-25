package org.thoughtcrime.securesms.scribbles;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import org.signal.core.util.logging.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

final class DuelFaceDetector implements FaceDetector {

  private static final String TAG = Log.tag(DuelFaceDetector.class);

  private final FaceDetector faceDetector1;
  private final FaceDetector faceDetector2;

  DuelFaceDetector(@NonNull FaceDetector faceDetector1, @NonNull FaceDetector faceDetector2) {
    this.faceDetector1 = faceDetector1;
    this.faceDetector2 = faceDetector2;
  }

  @Override
  public List<FaceDetector.Face> detect(@NonNull Bitmap bitmap) {
    List<FaceDetector.Face> detect1 = faceDetector1.detect(bitmap);
    List<FaceDetector.Face> detect2 = faceDetector2.detect(bitmap);

    ArrayList<FaceDetector.Face> rects = new ArrayList<>(detect1.size() + detect2.size());

    rects.addAll(detect1);
    rects.addAll(detect2);

    Log.d(TAG, String.format(Locale.US, "%s found %d faces, %s found %d faces", faceDetector1.getClass().getSimpleName(), detect1.size(), faceDetector2.getClass().getSimpleName(), detect2.size()));

    return rects;
  }
}
