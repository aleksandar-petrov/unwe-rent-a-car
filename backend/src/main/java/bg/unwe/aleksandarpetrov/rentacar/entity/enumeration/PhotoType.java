package bg.unwe.aleksandarpetrov.rentacar.entity.enumeration;

public enum PhotoType {
  JPEG("image/jpeg"),
  PNG("image/png"),
  GIF("image/gif");

  private final String contentType;

  PhotoType(String contentType) {
    this.contentType = contentType;
  }

  public String getContentType() {
    return contentType;
  }
}
