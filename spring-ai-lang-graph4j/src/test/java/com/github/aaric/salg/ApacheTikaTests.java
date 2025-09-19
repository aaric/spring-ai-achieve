package com.github.aaric.salg;

import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * ApacheTikaTests
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
//@Slf4j
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ApacheTikaTests {

    @Test
    public void testGetPdfText() throws Exception {
        Tika tika = new Tika();
        System.err.println(tika.parseToString(new File("/path/to/test.pdf")));
    }
}
