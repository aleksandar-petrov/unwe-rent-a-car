package bg.unwe.aleksandarpetrov.rentacar.service.impl;

import bg.unwe.aleksandarpetrov.rentacar.service.TransliterationService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class TransliterationServiceImpl implements TransliterationService {

  private static final Map<Character, String> CYRILLIC_MAP =
      Map.ofEntries(
          Map.entry('а', "a"),
          Map.entry('б', "b"),
          Map.entry('в', "v"),
          Map.entry('г', "g"),
          Map.entry('д', "d"),
          Map.entry('е', "e"),
          Map.entry('ё', "e"),
          Map.entry('ж', "zh"),
          Map.entry('з', "z"),
          Map.entry('и', "i"),
          Map.entry('й', "y"),
          Map.entry('к', "k"),
          Map.entry('л', "l"),
          Map.entry('м', "m"),
          Map.entry('н', "n"),
          Map.entry('о', "o"),
          Map.entry('п', "p"),
          Map.entry('р', "r"),
          Map.entry('с', "s"),
          Map.entry('т', "t"),
          Map.entry('у', "u"),
          Map.entry('ф', "f"),
          Map.entry('х', "h"),
          Map.entry('ц', "ts"),
          Map.entry('ч', "ch"),
          Map.entry('ш', "sh"),
          Map.entry('щ', "sht"),
          Map.entry('ъ', "a"),
          Map.entry('ы', "y"),
          Map.entry('ь', "j"),
          Map.entry('э', "e"),
          Map.entry('ю', "yu"),
          Map.entry('я', "ya"),
          Map.entry('А', "A"),
          Map.entry('Б', "B"),
          Map.entry('В', "V"),
          Map.entry('Г', "G"),
          Map.entry('Д', "D"),
          Map.entry('Е', "E"),
          Map.entry('Ё', "E"),
          Map.entry('Ж', "ZH"),
          Map.entry('З', "Z"),
          Map.entry('И', "I"),
          Map.entry('Й', "Y"),
          Map.entry('К', "K"),
          Map.entry('Л', "L"),
          Map.entry('М', "M"),
          Map.entry('Н', "N"),
          Map.entry('О', "O"),
          Map.entry('П', "P"),
          Map.entry('Р', "R"),
          Map.entry('С', "S"),
          Map.entry('Т', "T"),
          Map.entry('У', "U"),
          Map.entry('Ф', "F"),
          Map.entry('Х', "H"),
          Map.entry('Ц', "TS"),
          Map.entry('Ч', "CH"),
          Map.entry('Ш', "SH"),
          Map.entry('Щ', "SHT"),
          Map.entry('Ъ', "A"),
          Map.entry('Ы', "Y"),
          Map.entry('Ь', "Y"),
          Map.entry('Э', "E"),
          Map.entry('Ю', "YU"),
          Map.entry('Я', "YA"));

  @Override
  public String transliterate(String value) {
    var builder = new StringBuilder();
    for (int i = 0; i < value.length(); i++) {
      var lat = CYRILLIC_MAP.get(value.charAt(i));
      if (lat == null) {
        lat = value.charAt(i) + "";
      }

      builder.append(lat);
    }

    return builder.toString();
  }
}
