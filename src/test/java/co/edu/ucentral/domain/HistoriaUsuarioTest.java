package co.edu.ucentral.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.ucentral.web.rest.TestUtil;

public class HistoriaUsuarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriaUsuario.class);
        HistoriaUsuario historiaUsuario1 = new HistoriaUsuario();
        historiaUsuario1.setId(1L);
        HistoriaUsuario historiaUsuario2 = new HistoriaUsuario();
        historiaUsuario2.setId(historiaUsuario1.getId());
        assertThat(historiaUsuario1).isEqualTo(historiaUsuario2);
        historiaUsuario2.setId(2L);
        assertThat(historiaUsuario1).isNotEqualTo(historiaUsuario2);
        historiaUsuario1.setId(null);
        assertThat(historiaUsuario1).isNotEqualTo(historiaUsuario2);
    }
}
