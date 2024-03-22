package io.github.skippyall.vote.core.comment;

public interface CommentStorage extends Storage {
  Set<Comment> getComments(Vote vote);
  Comment getComment(long id);
  void addComment(Vote vote, Comment comment);
  void removeComment(Comment comment);
  void removeComment(long id);
}
