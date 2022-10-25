package org.smallchange.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.smallchange.backend.web.rest.TestUtil;

class TradeHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TradeHistory.class);
        TradeHistory tradeHistory1 = new TradeHistory();
        tradeHistory1.setId(1L);
        TradeHistory tradeHistory2 = new TradeHistory();
        tradeHistory2.setId(tradeHistory1.getId());
        assertThat(tradeHistory1).isEqualTo(tradeHistory2);
        tradeHistory2.setId(2L);
        assertThat(tradeHistory1).isNotEqualTo(tradeHistory2);
        tradeHistory1.setId(null);
        assertThat(tradeHistory1).isNotEqualTo(tradeHistory2);
    }
}
