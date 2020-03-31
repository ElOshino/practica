package co.edu.ucentral.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.ucentral.web.rest.TestUtil;

public class SignosVitalesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SignosVitales.class);
        SignosVitales signosVitales1 = new SignosVitales();
        signosVitales1.setId(1L);
        SignosVitales signosVitales2 = new SignosVitales();
        signosVitales2.setId(signosVitales1.getId());
        assertThat(signosVitales1).isEqualTo(signosVitales2);
        signosVitales2.setId(2L);
        assertThat(signosVitales1).isNotEqualTo(signosVitales2);
        signosVitales1.setId(null);
        assertThat(signosVitales1).isNotEqualTo(signosVitales2);
    }
}
