---populate building table
INSERT INTO odos_crrs_svc.bldg (bldg_id, bldg_nm, bldg_dsc) VALUES(1, 'Washington', 'The Building is located at North West gate of the campus');
INSERT INTO odos_crrs_svc.bldg (bldg_id, bldg_nm, bldg_dsc) VALUES(2, 'Lincoln', 'The Buliding is located at the North gate of the campus');
INSERT INTO odos_crrs_svc.bldg(bldg_id, bldg_nm, bldg_dsc) VALUES(3, 'Obama', 'The Buliding is located at the South gate of the campus');
----populate Conference room for building 1
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(1, 101, 'Atomics', 10, 'Y', 1);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(2, 102, 'IBX', 10, 'Y', 1);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(3, 103, 'Stratus', 10, 'Y', 1);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(4, 104, 'Atlas', 10, 'Y', 1);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(5, 105, 'Georgia', 10, 'Y', 1);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(6, 106, 'Capital', 10, 'Y', 1);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(7, 107, 'Blue', 10, 'Y', 1);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(8, 108, 'Sirus', 10, 'Y', 1);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(9, 109, 'Bast', 10, 'Y', 1);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(10, 110, 'Syphinics', 10, 'Y', 1);
----populate Conference room for building 2
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(11, 201, 'Carter', 10, 'Y', 2);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(12, 202, 'Regan', 10, 'Y', 2);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(13, 203, 'Truman', 10, 'Y', 2);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(14, 204, 'Roosevelt', 10, 'Y', 2);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(15, 205, 'Kennedy', 10, 'Y', 2);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(16, 206, 'Abrham', 10, 'Y', 2);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(17, 207, 'Polk', 10, 'Y', 2);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(18, 208, 'Madision', 10, 'Y', 2);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(19, 209, 'Admas', 10, 'Y', 2);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(20, 210, 'Monroe', 10, 'Y', 2);
----populate Conference room for building 3
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(21, 301, 'Chelsea', 10, 'Y', 3);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(22, 302, 'Everton', 10, 'Y', 3);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(23, 303, 'Arsenal', 10, 'Y', 3);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(24, 304, 'Manchester', 10, 'Y', 3);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(25, 305, 'Liverpool', 10, 'Y', 3);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(26, 306, 'Southampton', 10, 'Y', 3);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(27, 307, 'Watford', 10, 'Y', 3);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(28, 308, 'Fulham', 10, 'Y', 3);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(29, 309, 'Madrid', 10, 'Y', 3);
INSERT INTO odos_crrs_svc.conf_rm (conf_rm_id, rm_num, rm_name, rm_capcity, rm_active_ind, bldg_id) VALUES(30, 310, 'Barcelona', 10, 'Y', 3);
----populate equipment 
INSERT INTO odos_crrs_svc.equip (equip_id, equip_name, equip_desc) VALUES(1, 'TV', 'Samsung 64 inch 4K LED TV');
INSERT INTO odos_crrs_svc.equip (equip_id, equip_name, equip_desc) VALUES(2, 'Telephone', 'CISCO clear audio, hands-free capabilities');
INSERT INTO odos_crrs_svc.equip (equip_id, equip_name, equip_desc) VALUES(3, 'Projector', 'LG High Def');
---populate conf_rm_equip
INSERT INTO odos_crrs_svc.conf_rm_equip (conf_rm_equip_id, conf_rm_id, equip_id) VALUES(1, 1, 1);
INSERT INTO odos_crrs_svc.conf_rm_equip (conf_rm_equip_id, conf_rm_id, equip_id) VALUES(2, 11, 2);
INSERT INTO odos_crrs_svc.conf_rm_equip (conf_rm_equip_id, conf_rm_id, equip_id) VALUES(3, 21, 3);
INSERT INTO odos_crrs_svc.conf_rm_equip (conf_rm_equip_id, conf_rm_id, equip_id) VALUES(4, 2, 1);
INSERT INTO odos_crrs_svc.conf_rm_equip (conf_rm_equip_id, conf_rm_id, equip_id) VALUES(5, 3, 2);
INSERT INTO odos_crrs_svc.conf_rm_equip (conf_rm_equip_id, conf_rm_id, equip_id) VALUES(6, 4, 3);
