package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.ClientTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void chantierTest() {
        Client client = getClientRandomSampleGenerator();
        Chantier chantierBack = getChantierRandomSampleGenerator();

        client.addChantier(chantierBack);
        assertThat(client.getChantiers()).containsOnly(chantierBack);
        assertThat(chantierBack.getClient()).isEqualTo(client);

        client.removeChantier(chantierBack);
        assertThat(client.getChantiers()).doesNotContain(chantierBack);
        assertThat(chantierBack.getClient()).isNull();

        client.chantiers(new HashSet<>(Set.of(chantierBack)));
        assertThat(client.getChantiers()).containsOnly(chantierBack);
        assertThat(chantierBack.getClient()).isEqualTo(client);

        client.setChantiers(new HashSet<>());
        assertThat(client.getChantiers()).doesNotContain(chantierBack);
        assertThat(chantierBack.getClient()).isNull();
    }
}
