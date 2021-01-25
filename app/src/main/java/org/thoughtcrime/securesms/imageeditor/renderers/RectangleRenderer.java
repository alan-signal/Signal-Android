package org.thoughtcrime.securesms.imageeditor.renderers;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;

import androidx.annotation.NonNull;

import org.thoughtcrime.securesms.imageeditor.Bounds;
import org.thoughtcrime.securesms.imageeditor.Renderer;
import org.thoughtcrime.securesms.imageeditor.RendererContext;

public final class RectangleRenderer implements Renderer {

  private final int    color;
  private final Paint  paint;
  private final Matrix matrix;
  private final RectF  bounds;

  public RectangleRenderer(int color) {
    this.color = color;
    this.paint = new Paint();
    this.paint.setStyle(Paint.Style.STROKE);
    this.paint.setStrokeWidth(2);
    this.paint.setColor(color);
    this.matrix = new Matrix();
    this.bounds = new RectF();
  }

  @Override
  public void render(@NonNull RendererContext rendererContext) {
    paint.setStyle(Paint.Style.FILL);
    paint.setAlpha(64);
    rendererContext.canvas.drawRect(Bounds.FULL_BOUNDS, paint);
    paint.setAlpha(255);
    paint.setStyle(Paint.Style.STROKE);

    rendererContext.save();
    rendererContext.canvasMatrix.copyTo(matrix);
    matrix.mapRect(bounds, Bounds.FULL_BOUNDS);
    rendererContext.canvasMatrix.setToIdentity();

    // Now canvas is in device pixels
    rendererContext.canvas.drawRect(bounds, paint);
    rendererContext.restore();
  }

  @Override
  public boolean hitTest(float x, float y) {
    return Bounds.FULL_BOUNDS.contains(x, y);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(color);
  }

  public static final Creator<RectangleRenderer> CREATOR = new Creator<RectangleRenderer>() {
    @Override
    public RectangleRenderer createFromParcel(Parcel in) {
      return new RectangleRenderer(in.readInt());
    }

    @Override
    public RectangleRenderer[] newArray(int size) {
      return new RectangleRenderer[size];
    }
  };
}
