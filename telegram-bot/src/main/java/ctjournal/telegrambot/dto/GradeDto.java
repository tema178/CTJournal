package ctjournal.telegrambot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class GradeDto implements Serializable {

    private long id;

    private String french;

    private String uiaa;

    private String usa;

    private String norway;

    private String australian;

    private String southAfrica;

    public GradeDto(long id) {
        this.id = id;
    }

}
