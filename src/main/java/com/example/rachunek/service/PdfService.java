package com.example.rachunek.service;

import com.example.rachunek.dto.Cart;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public String parseBillTemplate(Cart cart) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        context.setVariable("cart", cart);

        return templateEngine.process("/templates/bill.html", context);
    }


    public ByteArrayInputStream generatePdfFromHtml(String html) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("/static/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
