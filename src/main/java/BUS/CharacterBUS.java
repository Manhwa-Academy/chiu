package BUS;

import DAO.CharacterDAO;
import DTO.ThuocTinhSanPham.CharacterDTO;
import java.util.ArrayList;

public class CharacterBUS {

    private final CharacterDAO characterDAO = new CharacterDAO();
    private ArrayList<CharacterDTO> listCharacter = new ArrayList<>();

    public CharacterBUS() {
        listCharacter = characterDAO.selectAll();
    }

    public ArrayList<CharacterDTO> getAll() {
        return this.listCharacter;
    }

    public CharacterDTO getByIndex(int index) {
        return this.listCharacter.get(index);
    }

    public String[] getArrTenNhanVat() {

        ArrayList<CharacterDTO> ds = this.getAll();

        String[] arr = new String[ds.size()];

        for (int i = 0; i < ds.size(); i++) {
            arr[i] = ds.get(i).getTencharacter();
        }

        return arr;
    }

    public int getIndexByTen(String ten) {

        ArrayList<CharacterDTO> ds = this.getAll();

        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getTencharacter().equalsIgnoreCase(ten)) {
                return i;
            }
        }

        return -1;
    }

    public int getIndexById(int macharacter) {
        for (int i = 0; i < listCharacter.size(); i++) {
            if (listCharacter.get(i).getMacharacter() == macharacter) {
                return i;
            }
        }
        return -1;
    }

    public boolean add(CharacterDTO character) {
        boolean check = characterDAO.insert(character) != 0;
        if (check) {
            this.listCharacter.add(character);
        }
        return check;
    }

    public boolean delete(CharacterDTO character, int index) {
        boolean check = characterDAO.delete(
                Integer.toString(character.getMacharacter())) != 0;
        if (check) {
            this.listCharacter.remove(index);
        }
        return check;
    }

    public boolean update(CharacterDTO character) {
        int index = getIndexById(character.getMacharacter());
        if (index == -1)
            return false;

        boolean check = characterDAO.update(character) != 0;
        if (check) {
            this.listCharacter.set(index, character);
        }
        return check;
    }

    /** ✅ LẤY TÊN CHARACTER THEO ID */
    public String getTenCharacterById(int macharacter) {
        int index = getIndexById(macharacter);
        if (index == -1)
            return "";
        return listCharacter.get(index).getTencharacter();
    }

    /** ✅ ĐỔ LÊN COMBOBOX */
    public String[] getArrTenCharacter() {
        String[] result = new String[listCharacter.size()];
        for (int i = 0; i < listCharacter.size(); i++) {
            result[i] = listCharacter.get(i).getTencharacter();
        }
        return result;
    }

    public String[] getArrKichThuoc() {
        // Giả sử mỗi nhân vật có một kích thước riêng biệt
        String[] kichThuocArr = new String[listCharacter.size()];
        for (int i = 0; i < listCharacter.size(); i++) {
            kichThuocArr[i] = listCharacter.get(i).getKichThuoc(); // Thay đổi nếu có thuộc tính kích thước trong
                                                                   // CharacterDTO
        }
        return kichThuocArr;
    }

    /** ✅ CHECK TRÙNG TÊN */
    public boolean checkDup(String ten) {
        for (CharacterDTO c : listCharacter) {
            if (c.getTencharacter().equalsIgnoreCase(ten.trim())) {
                return false;
            }
        }
        return true;
    }

}
