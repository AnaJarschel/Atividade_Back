
package br.unipar.backend.minhaapi.service;

import br.unipar.backend.minhaapi.dto.UsuarioDividaDTO;
import br.unipar.backend.minhaapi.model.Divida;
import br.unipar.backend.minhaapi.model.Usuario;
import br.unipar.backend.minhaapi.repository.DividaRepository;
import br.unipar.backend.minhaapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final DividaRepository dividaRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            DividaRepository dividaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.dividaRepository = dividaRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> editarUsuario(
            Integer id, Usuario usuario) {

        return usuarioRepository.findById(id)
                .map(u -> {
                    u.setNome(usuario.getNome());
                    u.setEmail(usuario.getEmail());
                    return usuarioRepository.save(u);
                });
    }

    public boolean deletarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            return false;
        }

        usuarioRepository.deleteById(id);
        return true;
    }

    public Optional<Divida> criarDivida(
            Integer usuarioId, Divida divida) {

        return usuarioRepository.findById(usuarioId)
                .map(usuario -> {
                    divida.setId(null);
                    divida.setUsuario(usuario);
                    return dividaRepository.save(divida);
                });
    }

    public Optional<UsuarioDividaDTO> consultarTotalDivida(
            Integer usuarioId) {

        return usuarioRepository.findById(usuarioId)
                .map(usuario -> {
                    BigDecimal total = usuario.getDividas()
                            .stream()
                            .map(Divida::getValor)
                            .filter(valor -> valor != null)
                            .reduce(BigDecimal.ZERO,
                                    BigDecimal::add);

                    return new UsuarioDividaDTO(
                            usuario.getNome(), total);
                });
    }
}
