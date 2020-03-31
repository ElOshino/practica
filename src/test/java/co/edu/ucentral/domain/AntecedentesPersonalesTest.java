package co.edu.ucentral.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.ucentral.web.rest.TestUtil;

public class AntecedentesPersonalesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AntecedentesPersonales.class);
        AntecedentesPersonales antecedentesPersonales1 = new AntecedentesPersonales();
        antecedentesPersonales1.setId(1L);
        AntecedentesPersonales antecedentesPersonales2 = new AntecedentesPersonales();
        antecedentesPersonales2.setId(antecedentesPersonales1.getId());
        assertThat(antecedentesPersonales1).isEqualTo(antecedentesPersonales2);
        antecedentesPersonales2.setId(2L);
        assertThat(antecedentesPersonales1).isNotEqualTo(antecedentesPersonales2);
        antecedentesPersonales1.setId(null);
        assertThat(antecedentesPersonales1).isNotEqualTo(antecedentesPersonales2);
    }
}
