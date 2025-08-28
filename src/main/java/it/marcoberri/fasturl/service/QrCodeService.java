package it.marcoberri.fasturl.service;

import io.nayuki.qrcodegen.QrCode;
import it.marcoberri.fasturl.data.repository.LogRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@ApplicationScoped
public class QrCodeService {

    @Inject
    LogRepository logRepository;


    public void generateQrCode(String url) throws IOException {
        // Simple operation
        QrCode qr0 = QrCode.encodeText(url, QrCode.Ecc.MEDIUM);
        BufferedImage img = toImage(qr0, 4, 10);  // See QrCodeGeneratorDemo
        ImageIO.write(img, "png", new File("qr-code.png"));

    }

    private static BufferedImage toImage(QrCode qr, int scale, int border) {
        return toImage(qr, scale, border, 0xFFFFFF, 0x000000);
    }

    private static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0)
            throw new IllegalArgumentException("Value out of range");
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale)
            throw new IllegalArgumentException("Scale or border too large");

        BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }


}
