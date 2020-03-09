package br.com.fernando.chapter00_dashboardMenu.part01_overall;

public class Part04_Config_And_Storage {

    // -------------------------------------------------------------------------------------------------------------------------------------------------------------
    //
    // Config and Storage
    //
    // * Config Maps:, ConfigMaps store general configuration information, such as a group of config files. 
    // Because ConfigMaps don’t store sensitive information, they can be updated automatically, and therefore don’t require their containers to be restarted following update
    //
    // * Persistent Volume Claims: You can define and apply a persistent volume claim to your cluster, which in turn creates a persistent volume that's bound to the claim.
    // A claim is a block storage volume in the underlying IaaS provider that's durable and offers persistent storage, enabling your data to remain intact, regardless of
    // whether the containers that the storage is connected to are terminated.
    //
    // * Secretes: Secrets store sensitive data like passwords, tokens, or keys. They may contain one or more key value pairs.
}
