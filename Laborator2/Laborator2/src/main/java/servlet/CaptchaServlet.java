package servlet;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

@WebServlet("/captcha")
public class CaptchaServlet extends HttpServlet {

    private Producer kaptchaProducer;

    @Override
    public void init() throws ServletException {
        // Configure Kaptcha Producer
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "200");
        properties.setProperty("kaptcha.image.height", "50");
        properties.setProperty("kaptcha.textproducer.font.size", "40");
        properties.setProperty("kaptcha.textproducer.char.length", "6");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");

        Config config = new Config(properties);
        kaptchaProducer = config.getProducerImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String captchaText = kaptchaProducer.createText();
        BufferedImage captchaImage = kaptchaProducer.createImage(captchaText);

        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaText);

        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();

        ImageIO.write(captchaImage, "png", out);
        out.flush();
        out.close();
    }
}
