Vagrant.configure(2) do |config|
    config.vm.network "private_network", ip: "192.168.50.4"
    config.vm.box = "ubuntu/xenial64"

    config.vm.provision :shell, path: "./provision.sh"

    config.vm.synced_folder "./", "/opt/vm-project", type: "nfs"

    config.vm.network "forwarded_port", guest: 8080, host: 8080 # www
    config.vm.network "forwarded_port", guest: 27017, host: 27017 # mongo

    config.vm.provider "virtualbox" do |vb|
      vb.gui = true
      vb.memory = "1024"
    end
end
