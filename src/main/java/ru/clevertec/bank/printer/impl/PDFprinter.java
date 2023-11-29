package ru.clevertec.bank.printer.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.printer.Print;

@Slf4j
public class PDFprinter implements Print<UserDto> {

    private static final String COVER = "src/main/resources/Clevertec_Template.pdf";
    public static final String FIRST_NAME = "First Name: ";
    public static final String LAST_NAME = "Last Name: ";
    public static final String MAIL = "Mail: ";
    public static final String AGE = "Age: ";
    private int startPosition = 650;

    @Override
    public void printToPDF(UserDto userDto) {
        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        Document document = null;
        try {
            pdfReader = new PdfReader(COVER);
            document = new Document();

            String fileName = "user_" + userDto.getId().toString() + ".pdf";
            pdfStamper = new PdfStamper(pdfReader,
                new FileOutputStream(createFile("userPDF", fileName)));

            Font font = FontFactory.getFont(FontFactory.COURIER, 16);
            PdfContentByte overContent = pdfStamper.getOverContent(1);
            document.open();

            overContent.beginText();
            overContent.setFontAndSize(font.getBaseFont(), font.getSize());
            overContent.showTextAligned(Element.ALIGN_LEFT, FIRST_NAME + userDto.getFirstName(),
                50, startPosition, 0);
            startPosition -= 20;

            overContent.showTextAligned(Element.ALIGN_LEFT, LAST_NAME + userDto.getLastName(),
                50, startPosition, 0);
            startPosition -= 20;

            overContent.showTextAligned(Element.ALIGN_LEFT, MAIL + userDto.getMail(),
                50, startPosition, 0);
            startPosition -= 20;

            overContent.showTextAligned(Element.ALIGN_LEFT, AGE + userDto.getAge(),
                50, startPosition, 0);

            overContent.endText();

        } catch (DocumentException | IOException e) {
            log.error(e.getMessage());
        } finally {
            if (Objects.nonNull(document)) {
                document.close();
            }
            if (Objects.nonNull(pdfStamper)) {
                try {
                    pdfStamper.close();
                } catch (DocumentException | IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (Objects.nonNull(pdfReader)) {
                pdfReader.close();
            }
        }

    }

    private File createFile(String folder, String fileName) {
        File createdFolder = new File(folder);
        File file = new File(createdFolder, fileName);
        if (!createdFolder.exists()) {
            createdFolder.mkdirs();
        }
        return file;
    }

}
