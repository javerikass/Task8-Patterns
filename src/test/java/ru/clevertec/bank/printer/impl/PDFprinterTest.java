package ru.clevertec.bank.printer.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.bank.dto.UserDto;
import ru.clevertec.bank.printer.impl.PDFprinter;

class PDFprinterTest {

    private PDFprinter pdfPrinter;

    @BeforeEach
    void setUp() {
        this.pdfPrinter = new PDFprinter();
    }

    @Test
    void printToPDFShouldCreatePdfFile() {
        // given
        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UserDto testUserDto = UserDto.builder()
            .id(uuid)
            .mail("johndoe@gmail.com")
            .firstName("John")
            .lastName("Doe")
            .age(25).build();

        // when
        pdfPrinter.printToPDF(testUserDto);

        //then
        assertTrue(Files.exists(Path.of("userPDF/user_123e4567-e89b-12d3-a456-426614174000.pdf")));
    }

}