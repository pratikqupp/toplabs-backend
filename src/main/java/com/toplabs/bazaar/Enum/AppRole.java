package com.toplabs.bazaar.Enum;

import java.util.*;

public enum AppRole {
    PATIENT("PATIENT"),
    DOCTOR("DOCTOR"),
    RECEPTIONIST("RECEPTIONIST"),
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    REGIONAL_ADMIN("REGIONAL_ADMIN"),
    FACILITY_MANAGER("FACILITY_MANAGER"),
    QUP_ACCOUNT_MANAGER("QUP_ACCOUNT_MANAGER"),
    VIRTUAL_RECEPTION("VIRTUAL_RECEPTION"),
    POS_PAYMENTS_MANAGER("POS_PAYMENTS_MANAGER"),
    RELATIONSHIP_MANAGER("RELATIONSHIP_MANAGER"),
    PHARMACIST("PHARMACIST")

    /* LAB MODULE SPECIFIC ROLE */
    /* ADMIN DEPARTMENT */,
    CEO("CEO"),
    SYSTEM_ADMIN("SYSTEM_ADMIN"),
    PHARMACY_ADMIN("PHARMACY_ADMIN"),

    /* LAB ROLES */
    PHLEBOTOMIST("PHLEBOTOMIST"),
    RUNNER("RUNNER"),
    OPERATOR ("OPERATOR"),

    /* SALES DEPARTMENT */
    SALES_REPRESENTATIVE("SALES_REPRESENTATIVE"),
    AREA_SALES_MANAGER("AREA_SALES_MANAGER"),
    REGIONAL_SALES_MANAGER("REGIONAL_SALES_MANAGER"),
    VERTICAL_HEAD("VERTICAL_HEAD"),
    LAB_HEAD("LAB_HEAD"),
    AREA_LAB_MANAGER("AREA_LAB_MANAGER"),
    REGIONAL_LAB_MANAGER("REGIONAL_LAB_MANAGER"),
    CARE_HEAD("CARE_HEAD"),
    CARE_MANAGER("CARE_MANAGER");






    private static Map<AppRole, List<AppRole>> compatibleRolesMap = new HashMap<>();


    static {
        compatibleRolesMap.put(AppRole.PATIENT, Arrays.asList(
                PATIENT, DOCTOR, RECEPTIONIST, FACILITY_MANAGER,
                QUP_ACCOUNT_MANAGER, VIRTUAL_RECEPTION, RELATIONSHIP_MANAGER
                ,PHLEBOTOMIST,AREA_SALES_MANAGER,REGIONAL_SALES_MANAGER,VERTICAL_HEAD,
                CEO,SYSTEM_ADMIN,SALES_REPRESENTATIVE,RUNNER,OPERATOR
        ));

        compatibleRolesMap.put(AppRole.DOCTOR, Arrays.asList(
                DOCTOR, PATIENT, FACILITY_MANAGER, VIRTUAL_RECEPTION
        ));

        compatibleRolesMap.put(AppRole.RECEPTIONIST, Arrays.asList(
                RECEPTIONIST, PATIENT, FACILITY_MANAGER,
                QUP_ACCOUNT_MANAGER, VIRTUAL_RECEPTION
        ));

        compatibleRolesMap.put(AppRole.SUPER_ADMIN, Arrays.asList(SUPER_ADMIN));

        compatibleRolesMap.put(AppRole.ADMIN, Arrays.asList(ADMIN));

        compatibleRolesMap.put(AppRole.REGIONAL_ADMIN, Arrays.asList(REGIONAL_ADMIN));

        compatibleRolesMap.put(AppRole.FACILITY_MANAGER, Arrays.asList(
                FACILITY_MANAGER, PATIENT, DOCTOR, RECEPTIONIST,
                QUP_ACCOUNT_MANAGER
        ));

        compatibleRolesMap.put(AppRole.QUP_ACCOUNT_MANAGER, Arrays.asList(
                QUP_ACCOUNT_MANAGER, PATIENT, RECEPTIONIST, FACILITY_MANAGER
        ));

        compatibleRolesMap.put(AppRole.VIRTUAL_RECEPTION, Arrays.asList(
                VIRTUAL_RECEPTION, PATIENT, DOCTOR, RECEPTIONIST
        ));

        compatibleRolesMap.put(AppRole.POS_PAYMENTS_MANAGER, Arrays.asList(
                POS_PAYMENTS_MANAGER, PATIENT, DOCTOR, RECEPTIONIST
        ));

        compatibleRolesMap.put(AppRole.RELATIONSHIP_MANAGER, Arrays.asList(
                RELATIONSHIP_MANAGER, PATIENT, FACILITY_MANAGER,
                QUP_ACCOUNT_MANAGER, VIRTUAL_RECEPTION
        ));

        compatibleRolesMap.put(AppRole.PHARMACIST, Arrays.asList(
                PATIENT,
                PHARMACIST
        ));

        compatibleRolesMap.put(AppRole.PHARMACY_ADMIN, Arrays.asList(
                PATIENT,
                PHARMACY_ADMIN
        ));

        compatibleRolesMap.put(AppRole.PHLEBOTOMIST, Arrays.asList(
                PATIENT,
                PHLEBOTOMIST
        ));

        compatibleRolesMap.put(AppRole.SALES_REPRESENTATIVE, Arrays.asList(
                PATIENT,
                SALES_REPRESENTATIVE
        ));

        compatibleRolesMap.put(AppRole.AREA_SALES_MANAGER, Arrays.asList(
                PATIENT,
                AREA_SALES_MANAGER
        ));

        compatibleRolesMap.put(AppRole.REGIONAL_SALES_MANAGER, Arrays.asList(
                PATIENT,
                REGIONAL_SALES_MANAGER
        ));

        compatibleRolesMap.put(AppRole.VERTICAL_HEAD, Arrays.asList(
                PATIENT,
                VERTICAL_HEAD
        ));

        compatibleRolesMap.put(AppRole.CEO, Arrays.asList(
                PATIENT,
                CEO
        ));

        compatibleRolesMap.put(AppRole.SYSTEM_ADMIN, Arrays.asList(
                PATIENT,
                SYSTEM_ADMIN
        ));

        compatibleRolesMap.put(AppRole.OPERATOR, Arrays.asList(
                PATIENT,
                OPERATOR
        ));

        compatibleRolesMap.put(AppRole.RUNNER, Arrays.asList(
                PATIENT,
                RUNNER
        ));
        compatibleRolesMap.put(AppRole.LAB_HEAD, Arrays.asList(
                PATIENT,
                LAB_HEAD
        ));
        compatibleRolesMap.put(AppRole.AREA_LAB_MANAGER, Arrays.asList(
                PATIENT,
                AREA_LAB_MANAGER
        ));

        compatibleRolesMap.put(AppRole.REGIONAL_LAB_MANAGER, Arrays.asList(
                PATIENT,
                REGIONAL_LAB_MANAGER
        ));

        compatibleRolesMap.put(AppRole.CARE_HEAD, Arrays.asList(
                PATIENT,
                CARE_HEAD
        ));

        compatibleRolesMap.put(AppRole.CARE_MANAGER, Arrays.asList(
                PATIENT,
                CARE_MANAGER
        ));

    }

    private String roleName;

    private AppRole(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return this.roleName;
    }

    /**
     * Check if the proposed role is compatible with the existing roles, will return
     * true if one of the existing roles is not allowed for the proposed role.
     *
     * @param existingRoles
     * @return
     */
    public boolean isNonCompatibleForExistingRoles(Set<AppRole> existingRoles) {
        List<AppRole> allowedRoles = compatibleRolesMap.get(this);

        return existingRoles.stream().anyMatch(t -> !allowedRoles.contains(t));
    }

    public static List<String> getCampAppLoginRoles() {
        return Arrays.asList(
                CEO.name(),
                SYSTEM_ADMIN.name(),
                SALES_REPRESENTATIVE.name(),
                AREA_SALES_MANAGER.name(),
                REGIONAL_SALES_MANAGER.name(),
                VERTICAL_HEAD.name(),
                PHLEBOTOMIST.name(),
                RUNNER.name(),
                LAB_HEAD.name(),
                AREA_LAB_MANAGER.name(),
                REGIONAL_LAB_MANAGER.name(),
                OPERATOR.name(),
                SUPER_ADMIN.name(),
                CARE_MANAGER.name(),
                CARE_HEAD.name()

        );
    }

    /**
     * Roles to whom notifications can be sent - language needs to be updated
     * accordingly
     *
     * @return
     */
    public static EnumSet<AppRole> notificationApplicableRoles() {
        return EnumSet.of(PATIENT, DOCTOR, RECEPTIONIST);
    }
}
