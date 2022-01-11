package hu.idne.backend.mappers.business;

import hu.idne.backend.mappers.system.AbstractMapper;
import hu.idne.backend.models.business.Post;
import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.models.business.dtos.PostDTO;
import hu.idne.backend.models.business.dtos.PostDocumentDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper extends AbstractMapper<Post, PostDTO> {

    private final PostDocumentMapper postDocumentMapper;

    public PostMapper(@Lazy PostDocumentMapper postDocumentMapper) {
        this.postDocumentMapper = postDocumentMapper;
    }

    @Override
    public PostDTO toDto(Post from, PostDTO to)
    {
        to = super.toDto(from, to);
        to.setId(from.getId());
        to.setTitle(from.getTitle());
        to.setContent(from.getContent());
        to.setVideoLink(from.getVideoLink());
        to.setPostDocuments(new ArrayList<>());
        if(from.getDocuments() !=null && !from.getDocuments().isEmpty())
        {
            List<PostDocumentDTO> postDocumentDTOList = new ArrayList<>();
            for (PostDocument postDocument : from.getDocuments())
            {
                postDocumentDTOList.add(postDocumentMapper.toDto(postDocument,new PostDocumentDTO()));
            }
            to.setPostDocuments(postDocumentDTOList);
        }

        return to;
    }

    @Override
    public Post toEntity(PostDTO from, Post to)
    {
        to = super.toEntity(from, to);
        to.setId(from.getId());
        to.setTitle(from.getTitle());
        to.setContent(from.getContent());
        to.setVideoLink(from.getVideoLink());
        to.setDocuments(new ArrayList<>());
        if(from.getPostDocuments() !=null && !from.getPostDocuments().isEmpty())
        {
            List<PostDocument> postDocumentDTOList = new ArrayList<>();
            for (PostDocumentDTO postDocument : from.getPostDocuments())
            {
                postDocumentDTOList.add(postDocumentMapper.toEntity(postDocument,new PostDocument()));
            }
            to.setDocuments(postDocumentDTOList);
        }

        return to;
    }
}
