package com.elorrieta.dto;

/*
 * 
 * DTO para devolver respuesta de subida de foto, incluyendo URL de la foto subida.
 */
public class UploadPhotoResponseDTO {
    private boolean success;
    private String message;
    private String photoUrl;

    public UploadPhotoResponseDTO() {}

    public UploadPhotoResponseDTO(boolean success, String message, String photoUrl) {
        this.success = success;
        this.message = message;
        this.photoUrl = photoUrl;
    }

    // Getters y setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}