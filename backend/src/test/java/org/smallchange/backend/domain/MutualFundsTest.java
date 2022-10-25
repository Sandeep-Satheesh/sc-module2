package org.smallchange.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.smallchange.backend.web.rest.TestUtil;

class MutualFundsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MutualFunds.class);
        MutualFunds mutualFunds1 = new MutualFunds();
        mutualFunds1.setId(1L);
        MutualFunds mutualFunds2 = new MutualFunds();
        mutualFunds2.setId(mutualFunds1.getId());
        assertThat(mutualFunds1).isEqualTo(mutualFunds2);
        mutualFunds2.setId(2L);
        assertThat(mutualFunds1).isNotEqualTo(mutualFunds2);
        mutualFunds1.setId(null);
        assertThat(mutualFunds1).isNotEqualTo(mutualFunds2);
    }
}
