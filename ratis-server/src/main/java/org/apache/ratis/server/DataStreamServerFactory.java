/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ratis.server;

import java.util.List;
import org.apache.ratis.conf.RaftProperties;
import org.apache.ratis.datastream.DataStreamFactory;
import org.apache.ratis.protocol.RaftPeer;
import org.apache.ratis.server.impl.ServerFactory;
import org.apache.ratis.statemachine.StateMachine;

public interface DataStreamServerFactory extends DataStreamFactory {

  static DataStreamServerFactory cast(DataStreamFactory dataStreamFactory) {
    if (dataStreamFactory instanceof DataStreamFactory) {
      return (DataStreamServerFactory)dataStreamFactory;
    }
    throw new ClassCastException("Cannot cast " + dataStreamFactory.getClass()
        + " to " + ServerFactory.class
        + "; rpc type is " + dataStreamFactory.getDataStreamType());
  }

  /**
   * Server implementation for streaming in Raft group
   */
  DataStreamServerRpc newDataStreamServerRpc(RaftPeer server, StateMachine stateMachine);

  /**
   * Server implementation for streaming in Raft group. The server will forward requests
   * to peers.
   */
  DataStreamServerRpc newDataStreamServerRpc(
      RaftPeer server, List<RaftPeer> peers, StateMachine stateMachine, RaftProperties properties);
}
