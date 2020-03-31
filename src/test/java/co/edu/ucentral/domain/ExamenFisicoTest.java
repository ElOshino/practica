package co.edu.ucentral.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.ucentral.web.rest.TestUtil;

public class ExamenFisicoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamenFisico.class);
        ExamenFisico examenFisico1 = new ExamenFisico();
        examenFisico1.setId(1L);
        ExamenFisico examenFisico2 = new ExamenFisico();
        examenFisico2.setId(examenFisico1.getId());
        assertThat(examenFisico1).isEqualTo(examenFisico2);
        examenFisico2.setId(2L);
        assertThat(examenFisico1).isNotEqualTo(examenFisico2);
        examenFisico1.setId(null);
        assertThat(examenFisico1).isNotEqualTo(examenFisico2);
    }
}
