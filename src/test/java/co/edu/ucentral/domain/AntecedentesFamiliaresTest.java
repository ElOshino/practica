package co.edu.ucentral.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.ucentral.web.rest.TestUtil;

public class AntecedentesFamiliaresTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AntecedentesFamiliares.class);
        AntecedentesFamiliares antecedentesFamiliares1 = new AntecedentesFamiliares();
        antecedentesFamiliares1.setId(1L);
        AntecedentesFamiliares antecedentesFamiliares2 = new AntecedentesFamiliares();
        antecedentesFamiliares2.setId(antecedentesFamiliares1.getId());
        assertThat(antecedentesFamiliares1).isEqualTo(antecedentesFamiliares2);
        antecedentesFamiliares2.setId(2L);
        assertThat(antecedentesFamiliares1).isNotEqualTo(antecedentesFamiliares2);
        antecedentesFamiliares1.setId(null);
        assertThat(antecedentesFamiliares1).isNotEqualTo(antecedentesFamiliares2);
    }
}
