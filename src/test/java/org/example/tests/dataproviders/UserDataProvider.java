package org.example.tests.dataproviders;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import org.example.tests.data.UserTestData;
import org.example.tests.utilities.JsonDataReader;
import org.testng.annotations.DataProvider;

public class UserDataProvider {
    @DataProvider(name = "users")
    public static Object[][] users() {
        List<UserTestData> users = JsonDataReader.readList(
                "testdata/users.json", new TypeReference<List<UserTestData>>() { });
        Object[][] data = new Object[users.size()][1];
        for (int index = 0; index < users.size(); index++) {
            data[index][0] = users.get(index);
        }
        return data;
    }
}
