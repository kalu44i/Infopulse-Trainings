package infopulse.galkin.lesson6;

import java.util.Objects;

/**
 * Created by apple on 1/24/15.
 *
 */
class CloneClone {
    public static class Clone implements Cloneable {
        int a = 10;
        int[] mas = new int[3];

        public Clone() {

        }

        public Object clone() throws CloneNotSupportedException {
            Clone a = (Clone)super.clone();
            return a;
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Clone p = new Clone();

        Clone p1 = (Clone)p.clone();
        p.mas[1] = 10;
        System.out.println(p1.mas[1]);
    }
}
