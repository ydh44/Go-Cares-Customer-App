package com.example.escort;

public class CG {

        private String cgname, cityname, cgumur, cgrating;

        public CG(String cgname, String cityname, String cgumur, String cgrating) {
            this.cgname = cgname;
            this.cityname = cityname;
            this.cgumur = cgumur;
            this.cgrating = cgrating;
        }

        public String getCgname() {
            return cgname;
        }

        public String getCityname() {
            return cityname;
        }

        public String getCgumur() {
            return cgumur;
        }

        public String getCgrating() {
            return cgrating;
        }
}
