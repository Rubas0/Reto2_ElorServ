package com.elorrieta.encriptado;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptSHA {

	/**
	 * Aplica SHA al texto pasado por parámetro y devuelve el hash
	 * 
	 */
	public String cifrarTexto(String texto) {
		MessageDigest algoritmo;
		try {
			algoritmo = MessageDigest.getInstance("SHA"); // Algoritmo a usar
			algoritmo.reset(); // Limpiamos la instancia por si acaso
			byte dataBytes[] = texto.getBytes(); 
			algoritmo.update(dataBytes); // El mensaje que queremos cifrar
			byte resumen[] = algoritmo.digest(); // Generamos el resumen

			return Hexadecimal(resumen);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Convierte Array de Bytes en hexadecimal
	static String Hexadecimal(byte[] resumen) {
		String HEX = "";
		for (int i = 0; i < resumen.length; i++) {
			String h = Integer.toHexString(resumen[i] & 0xFF);
			if (h.length() == 1)
				HEX += "0";
			HEX += h;
		}
		return HEX.toUpperCase();
	}
}