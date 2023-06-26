import org.WebClient;
import org.junit.Test;

import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataManager_checkUniqueLoginName_Test {
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument1() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (Objects.equals(resource, "/findOrgByName")) {
                    return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"name\":\"new org\",\"description\":\"this is the new org\", "
                            +
                            "\"funds\": [{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,"
                            +
                            "\"donations\":[{\"contributor\":\"1\", \"amount\":1, \"date\":\"2023-06-05T23:00:59.238Z\"}]}]}}";
                }
                return null;
            }
        });

        dm.checkUniqueLoginName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalIdArgument2() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (Objects.equals(resource, "/findOrgByName")) {
                    return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"name\":\"new org\",\"description\":\"this is the new org\", "
                            +
                            "\"funds\": [{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,"
                            +
                            "\"donations\":[{\"contributor\":\"1\", \"amount\":1, \"date\":\"2023-06-05T23:00:59.238Z\"}]}]}}";
                }
                return null;
            }
        });

        dm.checkUniqueLoginName("123@");
    }

    @Test
    public void testSuccessfulCreate() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (Objects.equals(resource, "/findOrgByName")) {
                    return "{\"status\":\"success\",\"data\":{\"_id\":\"123\",\"name\":\"new org\",\"description\":\"this is the new org\", "
                            +
                            "\"funds\": [{\"_id\":\"12345\",\"name\":\"new fund\",\"description\":\"this is the new fund\",\"target\":10000,"
                            +
                            "\"donations\":[{\"contributor\":\"1\", \"amount\":1, \"date\":\"2023-06-05T23:00:59.238Z\"}]}]}}";
                }
                return null;
            }
        });
        assertTrue(dm.checkUniqueLoginName("123"));
    }

    @Test
    public void testFailedCreate() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                if (Objects.equals(resource, "/findOrgByName")) {
                    return "{\"status\":\"error\",\"data\": null}";
                }
                return null;
            }
        });
        assertFalse(dm.checkUniqueLoginName("123"));
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateException() {
        DataManager dm = new DataManager(new WebClient("localhost", 3001) {
            @Override
            public String makeRequest(String resource, Map<String, Object> queryParams) {
                return null;
            }
        });
        dm.checkUniqueLoginName("123");
    }
}